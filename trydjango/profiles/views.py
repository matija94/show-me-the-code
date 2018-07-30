from django.contrib.auth.mixins import LoginRequiredMixin
from django.contrib.auth.models import User
from django.http import Http404
from django.shortcuts import get_object_or_404, redirect
from django.views import View

from django.views.generic import DetailView, ListView

from menus.models import Item
from profiles.models import Profile
from restaurants.models import Restaurant


class ProfileFollowToggle(View):

    def post(self, request, *args, **kwargs):
        toggled_username = request.POST['username']
        toggled_user_profile, is_following = Profile.objects.toggle_follow(request.user, toggled_username)
        return redirect(toggled_user_profile.get_absolute_url())


class ProfileListView(LoginRequiredMixin, ListView):
    model = Profile

    def get_queryset(self):
        queryset = Profile.objects.exclude(user=self.request.user)
        return queryset


class ProfileDetailView(LoginRequiredMixin, DetailView):
    template_name = 'profiles/user.html'

    def get_object(self, queryset=None):
        username = self.kwargs.get('username')
        if username is None:
            raise Http404
        return get_object_or_404(User, username__iexact=username, is_active=True)

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        items_exist = Item.objects.filter(user=context['user']).exists()
        locations_q = self.request.GET.get('q')
        locations = Restaurant.objects.filter(owner=context['user'], item__isnull=False).search(locations_q).distinct()
        is_following = False
        if self.request.user in context['user'].profile.followers.all():
            is_following = True
        context['is_following'] = is_following
        if items_exist and locations.exists():
            context['locations'] = locations
        return context

