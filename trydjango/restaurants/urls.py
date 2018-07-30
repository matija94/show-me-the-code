from django.urls import path

from restaurants.views import RestaurantListView, RestaurantCreateView, RestaurantUpdateView

app_name = 'restaurants'

urlpatterns = [
    path('', RestaurantListView.as_view(), name="list"),
    path('create/', RestaurantCreateView.as_view(), name="create"),
    path('<slug:slug>/', RestaurantUpdateView.as_view(), name="detail")

]
