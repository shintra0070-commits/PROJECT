import os
from pathlib import Path

from dotenv import load_dotenv
from flask import Flask, jsonify
from flask_cors import CORS

from .db import db
from .routes import complaints


def create_app(test_config=None):
    root = Path(__file__).resolve().parents[1]
    load_dotenv(root / ".env")

    required = ["SECRET_KEY", "DATABASE_URL"]
    missing = [name for name in required if not os.getenv(name)]
    if missing and test_config is None:
        raise RuntimeError(f"필수 환경변수가 없습니다: {', '.join(missing)}")

    app = Flask(__name__)
    app.config.update(
        SECRET_KEY=os.getenv("SECRET_KEY", "test-only"),
        SQLALCHEMY_DATABASE_URI=os.getenv("DATABASE_URL", "sqlite:///:memory:"),
        SQLALCHEMY_TRACK_MODIFICATIONS=False,
        SQLALCHEMY_ENGINE_OPTIONS={"pool_pre_ping": True},
        DEBUG=os.getenv("FLASK_ENV") == "development",
        JSON_AS_ASCII=False,
    )
    if test_config:
        app.config.update(test_config)

    db.init_app(app)
    allowed_origins = [origin.strip() for origin in os.getenv(
        "CORS_ORIGINS", "http://localhost:3000"
    ).split(",") if origin.strip()]
    CORS(app, resources={r"/api/*": {"origins": allowed_origins}})
    app.register_blueprint(complaints)

    @app.get("/api/health")
    def health():
        return {"status": "ok"}

    @app.errorhandler(404)
    def not_found(_error):
        return jsonify(error="NOT_FOUND", message="요청한 API를 찾을 수 없습니다."), 404

    return app
