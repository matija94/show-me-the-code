from django.shortcuts import render, redirect


def welcome(request):
    if request.user.is_authenticated:
        return redirect('player:player_home')
    else:
        return render(request, "tictactoe/game.html")