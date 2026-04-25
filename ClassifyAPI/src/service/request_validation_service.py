from src.dependencies.config import internal_key
from src.domain.text_dto import TextDTO
from src.error.exceptions import TextOutOfBounds, TextBlank, IncorrectKeyException

class RequestValidationService:
    def __init__(self):
        self.limit_len: int = 1000

    def execute_all(self, text_dto_list: list[TextDTO], api_key: str):
        self.__valid_key(api_key)

        for text_dto in text_dto_list:
            self.__valid_text(text_dto.text)

    def execute(self, text_dto: TextDTO, api_key:str):
        self.__valid_key(api_key)
        self.__valid_text(text_dto.text)

    def __valid_key(self, api_key: str):
        if api_key.strip() != internal_key:
            raise IncorrectKeyException()


    def __valid_text(self, text: str):
        if len(text) > self.limit_len:
            raise TextOutOfBounds()
        if text == "":
            raise TextBlank()
