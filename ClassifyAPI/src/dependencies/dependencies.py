from src.service.emotion_classifier_service import EmotionClassifierService
from src.service.request_validation_service import RequestValidationService

__emotion_classifier_service = EmotionClassifierService()
def get_emotion_classifier():
    return __emotion_classifier_service


__request_validation_service = RequestValidationService()
def get_request_validation_service():
    return __request_validation_service