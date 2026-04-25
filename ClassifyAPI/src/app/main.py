from fastapi import FastAPI

from src.controller import classifier_controller
from src.error.exceptions import IncorrectKeyException, TextOutOfBounds, TextBlank

from src.error.exceptions_handler import (
    incorrect_key,
    larger_text,
    blank_text
)

app = FastAPI(
    title="Text Classification API",
    description="An API that classifies text in positive and negative",
    version="1.0"
)

app.include_router(classifier_controller.route)

app.add_exception_handler(IncorrectKeyException, incorrect_key)
app.add_exception_handler(TextOutOfBounds, larger_text)
app.add_exception_handler(TextBlank, blank_text)
