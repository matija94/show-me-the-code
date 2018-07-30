from django.urls import path

from .views import ItemListView, ItemCreateView, ItemUpdateView

app_name = 'menus'

urlpatterns = [
    path('', ItemListView.as_view(), name='list'),
    path('create/', ItemCreateView.as_view(), name="create"),
    path('<int:pk>/', ItemUpdateView.as_view(), name="detail"),


]
