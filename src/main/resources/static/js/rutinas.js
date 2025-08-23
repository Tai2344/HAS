document.getElementById("btnAgendar").addEventListener("click", () => {
    fetch("/reservas/agendar", {method: "POST"})
            .then(res => res.text())
            .then(data => {
                if (data === "ok") {
                    let toast = new bootstrap.Toast(document.getElementById("toastCita"));
                    toast.show();
                }
            })
            .catch(err => console.error("Error:", err));
});
