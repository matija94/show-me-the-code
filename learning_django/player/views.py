from django.contrib.auth.decorators import login_required
from django.shortcuts import render

# Create your views here.
from gameplay.models import Game

@login_required
def home(request):

    user_games = Game.objects.games_for_user(request.user)
    active_games = Game.objects.active()

    return render(request, "player/home.html",
                            {"games":active_games}
                  )