from django.urls import path

from player import views

app_name = 'player'
urlpatterns = [
    # /player/
    path('', views.home, name='player_home'),
    path('login', views.PlayerLoginView.as_view(), name="player_login"),
    path('logout', views.PlayerLogoutView.as_view(), name="player_logout"),
    path('invitation', views.new_invitation, name="player_invitation"),
    path('accept_invitation/<int:invitation_id>', views.accept_invitation, name="player_accept_invitation"),
    path('signup', views.SignUpView.as_view(), name="player_signup")
]