from fastapi.encoders import jsonable_encoder
from starlette.responses import JSONResponse
from fastapi import Request

from src.error.exceptions import IncorrectKeyException, TextOutOfBounds, TextBlank

async def incorrect_key(request: Request, exc: IncorrectKeyException):
    return JSONResponse(
        status_code= 401,
        content=jsonable_encoder({"detail":exc.error, "body":str(exc)})
    )

async def larger_text(request: Request, exc: TextOutOfBounds):
    return JSONResponse(
        status_code= 406,
        content= jsonable_encoder({"detail":exc.error, "body":str(exc)})
    )

async def blank_text(request: Request, exc: TextBlank):
    return JSONResponse(
        status_code= 400,
        content= jsonable_encoder({"detail":exc.error, "body":str(exc)})
    )