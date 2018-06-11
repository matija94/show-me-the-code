from django.urls import path

from snippets import views

app_name = 'snippets-api'
'''
router = DefaultRouter()
router.register('snippets', views.SnippetViewSet)
router.register('users', views.UserViewSet)
'''
urlpatterns = [

    path('', views.api_root, name="home"),
    path('snippets/', views.SnippetList.as_view(), name="snippet-list"),
    path('users/', views.UserList.as_view(), name="user-list"),
    path('snippets/<int:pk>', views.SnippetDetail.as_view(), name='snippet-detail'),
    path('snippets/<int:pk>/highlight/', views.SnippetHighlight.as_view(), name='snippet-highlight'),
    path('users/<int:pk>', views.UserDetail.as_view(), name='user-detail'),
]
