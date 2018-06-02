from django.urls import path

from tictactoe import views

app_name = 'tictactoe'
urlpatterns = [
    # /tictactoe/
    path('', views.welcome, name='welcome')
]