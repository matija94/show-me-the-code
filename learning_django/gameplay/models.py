from django.db import models
from django.contrib.auth.models import User
from django.db.models import Q


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

    def __str__(self):
        return '{0} vs {1}'.format(self.first_player, self.second_player)

# Create your models here.
class Move(models.Model):
    x = models.IntegerField()
    y = models.IntegerField()
    comment = models.CharField(max_length=300, blank=True)
    by_first_player = models.BooleanField()
    game = models.ForeignKey(Game, on_delete=models.CASCADE)

    def __str__(self):
        return '[{0},{1}], comment={2}\t first={3}\t game={4}' \
                .format(self.x, self.y, self.comment, self.by_first_player,
                        str(self.game))