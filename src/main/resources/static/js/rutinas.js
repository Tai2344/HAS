//Pa mostrar modal
document.addEventListener("DOMContentLoaded", function () {
    const modalEl = document.getElementById("modalDuplicado");
    if (modalEl && modalEl.dataset.show === "true") {
        const modal = new bootstrap.Modal(modalEl);
        modal.show();
    }

    // Esto formatea elementos data-dia a Dia DD de Mes 
    const diaElems = document.querySelectorAll("[data-dia]");
    if (diaElems.length > 0) {
        //Hace con numeros un mapeo de todos los numeros, empieza con domingo 0, sí, raro pero bueno, así encontré la función
        const dayMap = {
            "domingo": 0, "lunes": 1, "martes": 2, "miércoles": 3, "miercoles": 3,
            "jueves": 4, "viernes": 5, "sábado": 6, "sabado": 6
        };
        // Esto enlista los nombres de los dias y meses
        const dias = ["Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"];
        const meses = ["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"];
        // Esto obtiene la fecha actual del sistema
        const hoy = new Date();
        // Itera sobre cada elemento data-dia
        diaElems.forEach(el => {
            // Obtiene el día de la semana desde el atributo data-dia o el contenido del elemento
            const raw = (el.dataset.dia || el.textContent || "").trim().toLowerCase();
            const objetivo = dayMap[raw];
            if (objetivo === undefined) return;
            // Con esto se calcula cuántos días faltan para el próximo día objetivo
            const diff = (objetivo - hoy.getDay() + 7) % 7;
            // Crea una nueva fecha sumando los días de diferencia, recordemos que en los talleres si hoy es martes y se reserva
            // un taller del lunes, aparecerá la fecha del proximo lunes, no el que pasó
            const fecha = new Date(hoy.getFullYear(), hoy.getMonth(), hoy.getDate() + diff);
            //Esto actualiza el contenido de la fecha en el formato mencionado antes 
            el.textContent = `${dias[fecha.getDay()]} ${fecha.getDate()} de ${meses[fecha.getMonth()]}`;
        });
    }
});