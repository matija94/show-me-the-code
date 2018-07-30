from django.urls import path

from .views import ProfileDetailView, ProfileListView, ProfileFollowToggle

app_name = 'profiles'

urlpatterns = [
    path('', ProfileListView.as_view(), name='list'),
    path('follow', ProfileFollowToggle.as_view(), name='follow'),
    path('<slug:username>', ProfileDetailView.as_view(), name="detail")
]
