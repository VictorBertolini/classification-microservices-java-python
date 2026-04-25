from typing import Annotated

from fastapi import APIRouter
from fastapi.params import Depends, Header
from src.dependencies.dependencies import get_emotion_classifier
from src.dependencies.dependencies import get_request_validation_service

from src.domain.text_dto import TextDTO
from src.service.emotion_classifier_service import EmotionClassifierService
from src.service.request_validation_service import RequestValidationService

route = APIRouter(prefix="/classify")

@route.post("")
async def classify_text(request: TextDTO,
                  classifier: Annotated[EmotionClassifierService, Depends(get_emotion_classifier)],
                  validation_service: Annotated[RequestValidationService, Depends(get_request_validation_service)],
                  api_key: Annotated[str, Header(convert_underscores=False)]) -> dict:
    validation_service.execute(request, api_key)
    return classifier.classify(request)

@route.post("/batch")
async def classify_texts(requests: list[TextDTO],
                   classifier: Annotated[EmotionClassifierService, Depends(get_emotion_classifier)],
                    validation_service: Annotated[RequestValidationService, Depends(get_request_validation_service)],
                   api_key: Annotated[str, Header(convert_underscores=False)]) -> list[dict]:
    validation_service.execute_all(requests, api_key)
    return classifier.classify_all(requests)

