from complaint_backend import create_app
import os

app = create_app()

if __name__ == "__main__":
    if app.config["DEBUG"]:
        app.run(host="0.0.0.0", port=5001, debug=True)
    else:
        from waitress import serve
        serve(app, host="0.0.0.0", port=int(os.getenv("PORT", "5001")))
