from django.contrib.auth.decorators import login_required
from django.core.exceptions import PermissionDenied
from django.shortcuts import render, get_object_or_404, redirect

# Create your views here.
from django.views.generic import ListView

from gameplay.forms import MoveForm
from gameplay.models import Game


class GamesListView(ListView):
    model = Game
    template_name = "gameplay/home.html"


def home(request):
    active_games = Game.objects.active()
    return render(request, "gameplay/home.html", {"active_games" : active_games})

@login_required
def game_detail(request, game_id):
    game = get_object_or_404(Game, pk=game_id)
    context = {"game" : game}
    if game.is_users_move(request.user):
        context['form'] = MoveForm()
    return render(request, "gameplay/game_detail.html", context)


@login_required
def make_move(request, game_id):
    game = get_object_or_404(Game, pk=game_id)
    if not game.is_users_move(request.user):
        raise PermissionDenied
    move = game.new_move()
    form = MoveForm(instance=move, data=request.POST)
    if form.is_valid():
        move.save()
        return redirect("game:gameplay_detail", game_id)
    else:
        return render(request, "gameplay/game_detail.html", {"game": game, "form": form})