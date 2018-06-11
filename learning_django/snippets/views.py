from django.contrib.auth.models import User

# Create your views here.
from rest_framework import permissions, renderers
from rest_framework.reverse import reverse
from rest_framework.decorators import api_view
from rest_framework.generics import RetrieveUpdateDestroyAPIView, ListCreateAPIView, ListAPIView, RetrieveAPIView, \
    GenericAPIView
from rest_framework.response import Response

from snippets.models import Snippet
from snippets.permissions import IsOwnerOrReadOnly
from snippets.serializers import SnippetSerializer, UserSerializer


class SnippetMixin(object):
    queryset = Snippet.objects.all()
    serializer_class = SnippetSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly,)


class UserMixin(object):
    queryset = User.objects.all()
    serializer_class = UserSerializer


@api_view(["GET"])
def api_root(request, format=None):
    return Response({
        'users': reverse('snippets-api:user-list', request=request, format=format),
        'snippets': reverse('snippets-api:snippet-list', request=request, format=format)
    })


class SnippetHighlight(GenericAPIView):
    queryset = Snippet.objects.all()
    renderer_classes = (renderers.StaticHTMLRenderer,)

    def get(self, request, *args, **kwargs):
        snippet = self.get_object()
        return Response(snippet.highlighted)


class SnippetDetail(SnippetMixin, RetrieveUpdateDestroyAPIView):
    pass


class SnippetList(SnippetMixin, ListCreateAPIView):

    def perform_create(self, serializer):
        serializer.save(owner=self.request.user)


class UserList(UserMixin, ListAPIView):
    pass


class UserDetail(UserMixin, RetrieveAPIView):
    pass