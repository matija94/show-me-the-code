from rest_framework import serializers

from api.models import Task


class TaskSerializer(serializers.ModelSerializer):

    owner = serializers.ReadOnlyField(source='owner.username')

    class Meta:
        model = Task
        fields = ['title', 'description', 'completed', 'owner']
