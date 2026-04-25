class IncorrectKeyException(Exception):
    def __init__(self, message:str="The key send is invalid"):
        super().__init__(message)
        self.error = "INVALID KEY"

class TextOutOfBounds(Exception):
    def __init__(self, message: str = "Text larger than it can be classified"):
        super().__init__(message)
        self.error = "TEXT OUT OF BOUNDS"

class TextBlank(Exception):
    def __init__(self, message: str = "Text blank is invalid"):
        super().__init__(message)
        self.error = "TEXT BLANK"