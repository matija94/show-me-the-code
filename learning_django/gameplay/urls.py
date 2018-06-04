from django.urls import path

from gameplay import views


app_name = "game"
urlpatterns = [
    path('', views.GamesListView.as_view(), name="gameplay_home"),
    path('detail/<int:game_id>', views.game_detail, name="gameplay_detail"),
    path('make_move/<int:game_id>', views.make_move, name="gameplay_make_move")

]