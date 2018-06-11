from django.urls import path

from api import views


app_name = 'task_api'
urlpatterns = [

    path('tasks/', views.TaskList.as_view(), name="task_list"),
    path('tasks/<int:pk>', views.TaskDetail.as_view(), name='task_detail')
]
