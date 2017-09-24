from bs4.element import Tag
from base.HtmlPage import HtmlPage

class HtmlPageUtils:
    
    @staticmethod
    def get_path(tag):
        '''
        Returns absolute path of the tag in the DOM
        
        Consider this DOM tree:
        <html>
            <header></header>
            <body>
                <article>
                    <div></div>
                    <div>
                        <p>target</p> ---> TARGET_ELEMENT
                    </div>
                </article>
            </body>
        </html>
                    
        This function would return for TARGET_ELEMENT html1.body2.article1.div2.p1
        '''
        if not type(tag) is Tag:
            raise TypeError('not bs4.Tag')
        def _get_path(tag,prev_tag,path,position):
            if position == -1:
                contents_normalized = list(filter(lambda e: isinstance(e, Tag),tag.contents))
                position = contents_normalized.index(prev_tag)
            if len(path) > 0:
                path[-1] = path[-1] + str(position+1)
            path.append(tag.name)
            if tag.parent is None:
                if tag.previous_sibling is None:
                    return
                else:
                    parent = tag.parent
                    contents_normalized = list(filter(lambda e: isinstance(e, Tag),tag.contents))
                    position = parent.contents_normalized.index(tag)
                    _get_path(tag.previous_sibling,tag,path,position)
            else:
                _get_path(tag.parent,tag,path,-1)
        
        path = list()
        pos = tag.parent.contents.index(tag)
        _get_path(tag,None,path,pos)
        normalized_path = reversed(path)
        return '.'.join(normalized_path)
    
if __name__ == '__main__':
    htmlPage = HtmlPage('''
        <html>
            <header></header>
            <body>
                <article>
                    <div>
                        <button>
                            <span> Add to Cart </span>
                        </button>
                    </div>
                    <div>
                        <p>target</p>
                    </div>
                </article>
            </body>
        </html>
                        ''')
    
    
    e = htmlPage.find_all_tags_containing_text(['button','span'], ['cart','Cart'], None)
    print(e)
    