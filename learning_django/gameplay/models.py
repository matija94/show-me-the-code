from django.core.validators import MinValueValidator, MaxValueValidator
from django.db import models
from django.contrib.auth.models import User
from django.db.models import Q
from django.urls import reverse


class GamesQuerySet(models.QuerySet):

    def games_for_user(self,user):
        return self.filter(
            Q(first_player=user) | Q(second_player=user)
        )

    def active(self):
        return self.filter(
            Q(status="F") | Q(status="S")
        )


class Game(models.Model):

    BOARD_SIZE = 3

    STATUS_CHOICES = (
        ("F", "First player to move"),
        ("S", "Second player to move"),
        ("W", "First player wins"),
        ("L", "First player loses"),
        ("D", "Draw")
    )

    first_player = models.ForeignKey(User, related_name="games_first_player", on_delete=models.CASCADE)
    second_player = models.ForeignKey(User, related_name="games_second_player", on_delete=models.CASCADE)
    start_time = models.DateTimeField(auto_now_add=True)
    last_active = models.DateTimeField(auto_now=True)
    status = models.CharField(max_length=1, default='F', choices=STATUS_CHOICES)

    objects = GamesQuerySet.as_manager()

    def is_users_move(self, user):
        return (user == self.first_player and self.status == "F") or (user == self.second_player and self.status == "S")

    def board(self):
        """ Return a 2-dimensional list of Move objects """
        board = [[None for x in range(Game.BOARD_SIZE)] for y in range(Game.BOARD_SIZE)]
        for move in self.move_set.all():
            board[move.y][move.x] = move
        return board

    def new_move(self):
        """ Returns a new move object with player, game and count preset """
        if self.status not in "FS":
            raise ValueError("Cannot make move on finished game")

        return Move(game=self, by_first_player=self.status == "F")

    def get_absolute_url(self):
        return reverse("game:gameplay_detail", args=[self.id])

    def update_after_move(self, move):
        self.status = self.__get_status_after_move(move)

    def __get_status_after_move(self,move):
        x, y = move.x, move.y
        board = self.board()
        if (board[y][0] == board[y][1] == board[y][2] or
            board[0][x] == board[1][x] == board[2][x] or
            board[0][0] == board[1][1] == board[2][2] or
            board[0][2] == board[1][1] == board[2][0]):
                return 'W' if self.status == 'F' else 'L'
        if self.move_set.count() >= Game.BOARD_SIZE**2:
            return 'D'
        return 'S' if self.status == 'F' else 'F'

    def __str__(self):
        return '{0} vs {1}'.format(self.first_player, self.second_player)

# Create your models here.
class Move(models.Model):
    x = models.IntegerField(validators=[MinValueValidator(0), MaxValueValidator(Game.BOARD_SIZE-1)])
    y = models.IntegerField(validators=[MinValueValidator(0), MaxValueValidator(Game.BOARD_SIZE-1)])
    comment = models.CharField(max_length=300, blank=True)
    by_first_player = models.BooleanField(editable=False)
    game = models.ForeignKey(Game, on_delete=models.CASCADE, editable=False)

    def __str__(self):
        return '[{0},{1}], comment={2}\t first={3}\t game={4}' \
                .format(self.x, self.y, self.comment, self.by_first_player,
                        str(self.game))

    def __eq__(self, other):
        if other is None:
            return False
        return other.by_first_player == self.by_first_player

    def save(self, *args, **kwargs):
        super().save(*args, **kwargs)
        self.game.update_after_move(self)
        self.game.save()


