from django.contrib.auth.decorators import login_required
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.views import LoginView, LogoutView
from django.shortcuts import render, redirect, get_object_or_404

# Create your views here.
from django.urls import reverse_lazy
from django.views.generic import CreateView

from gameplay.models import Game
from player.forms import InvitationForm
from player.models import Invitation


class PlayerLoginView(LoginView):
    template_name = 'player/login_form.html'

class PlayerLogoutView(LogoutView):
    pass

@login_required
def home(request):

    user_games = Game.objects.games_for_user(request.user)
    active_games = user_games.active()
    finished_games = user_games.difference(active_games)
    invitations = request.user.received_invitations.all()
    return render(request, "player/home.html",
                            {"active_games":active_games, "finished_games":finished_games, "invitations":invitations}
                  )

@login_required
def new_invitation(request):
    if request.method == "POST":
        invitation = Invitation(from_user=request.user)
        form = InvitationForm(instance=invitation, data=request.POST)
        if form.is_valid():
            form.save()
            return redirect("player:player_home")
    else:
        form = InvitationForm()
    return render(request, 'player/invitation_form.html', {"form":form})

@login_required
def accept_invitation(request,invitation_id):
    invitation = get_object_or_404(Invitation, pk=invitation_id)
    if not request.user == invitation.to_user:
        raise PermissionError
    if request.method == "POST":
        if "accept" in request.POST:
            game = Game.objects.create(first_player=invitation.from_user, second_player=invitation.to_user)
            invitation.delete()
            return redirect(game)
    else:
        return render(request, "player/accept_invitation_form.html", {"invitation":invitation})

class SignUpView(CreateView):
    form_class = UserCreationForm
    template_name = "player/signup_form.html"
    success_url = reverse_lazy("player:player_home")