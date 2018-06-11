from django.contrib.auth.models import User
from rest_framework import serializers

from snippets.models import Snippet


class SnippetSerializer(serializers.HyperlinkedModelSerializer):
    owner = serializers.ReadOnlyField(source='owner.username')
    highlight = serializers.HyperlinkedIdentityField(view_name='snippets-api:snippet-highlight', format='html')
    url = serializers.HyperlinkedIdentityField(view_name='snippets-api:snippet-detail')

    class Meta:
        model = Snippet
        fields = ('url', 'id', 'owner', 'highlight',
                  'title', 'code', 'linenos', 'language', 'style')


class UserSerializer(serializers.HyperlinkedModelSerializer):
    snippets = serializers.HyperlinkedRelatedField(many=True, read_only=True, view_name='snippets-api:snippet-detail')
    url = serializers.HyperlinkedIdentityField(view_name='snippets-api:user-detail')

    class Meta:
        model = User
        fields = ('url', 'id', 'username', 'snippets')
