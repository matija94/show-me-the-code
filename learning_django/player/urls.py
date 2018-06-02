from django.urls import path

from player import views

app_name = 'player'
urlpatterns = [
    # /player/
    path('', views.home, name='player_home')
]