from django.contrib.auth.mixins import LoginRequiredMixin
from django.shortcuts import render
from django.views import View
from django.views.generic import ListView, CreateView, UpdateView

from menus.forms import ItemForm
from .models import Item


class HomeView(LoginRequiredMixin, View):

    def get(self, request, *args, **kwargs):
        if not request.user.is_authenticated:
            return render(request, "home.html", {})

        user = request.user
        user_following_ids = [x.user.id for x in user.user_following.all()]
        qs = Item.objects.filter(user__id__in=user_following_ids, public=True).order_by('-updated')[:3]
        print(qs)

        return render(request, 'menus/home-feed.html', {'object_list': qs})


class ItemListView(LoginRequiredMixin, ListView):

    def get_queryset(self):
        return Item.objects.filter(user=self.request.user)


class ItemCreateView(LoginRequiredMixin, CreateView):
    form_class = ItemForm
    template_name = 'form.html'

    def form_valid(self, form):
        obj = form.save(commit=False)
        obj.user = self.request.user
        return super().form_valid(form)

    def get_form_kwargs(self):
        kwargs = super().get_form_kwargs()
        kwargs['user'] = self.request.user
        return kwargs

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['title'] = 'Add Item'
        return context


class ItemUpdateView(LoginRequiredMixin, UpdateView):
    form_class = ItemForm
    template_name = 'menus/item_detail.html'
    model = Item

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['title'] = 'Update Item'
        return context

    def get_form_kwargs(self):
        kwargs = super().get_form_kwargs()
        kwargs['user'] = self.request.user
        return kwargs

    def get(self, request, *args, **kwargs):
        response = super().get(request, *args, **kwargs)
        if self.object.user != self.request.user:
            raise PermissionError("You are not the owner of this item")
        return response
