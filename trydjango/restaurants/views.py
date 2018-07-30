from django.contrib.auth.mixins import LoginRequiredMixin
from django.views.generic import ListView, DetailView, CreateView, UpdateView

from .models import Restaurant
from .forms import RestaurantCreateFormEasy


class RestaurantListView(LoginRequiredMixin, ListView):
    login_url = '/login/'

    def get_queryset(self):
        return Restaurant.objects.all().filter(owner=self.request.user)


class RestaurantCreateView(LoginRequiredMixin, CreateView):
    form_class = RestaurantCreateFormEasy
    template_name = 'form.html'
    login_url = '/login/'

    def form_valid(self, form):
        instance = form.save(commit=False)
        instance.owner = self.request.user
        return super().form_valid(form)

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['title'] = 'Add Restaurant'
        return context


class RestaurantUpdateView(LoginRequiredMixin, UpdateView):
    form_class = RestaurantCreateFormEasy
    template_name = 'restaurants/restaurant_detail.html'
    login_url = '/login/'
    model = Restaurant

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['title'] = 'Update Restaurant'
        return context
