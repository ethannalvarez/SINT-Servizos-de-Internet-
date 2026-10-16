console.log("El script p1.js está conectado y funcionando.");

document.getElementById('tvForm').addEventListener('submit', function(e){
    let errores = [];
    const login = document.getElementById('clogin').value;
    const pwd = document.getElementById('cpasswd').value;
    const dni = document.getElementById('cdni').value;

    if (login && !/^[a-z0-9]{4,8}$/.test(login)) {
        errores.push("- Login: Debe tener entre 4 y 8 caracteres (solo números y letras minúsculas).");
    }
    if (pwd && !/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\+\-\*\/])[a-zA-Z\d\+\-\*\/]{6,12}$/.test(pwd)) {
        errores.push("- Contraseña: 6-12 caracteres, al menos una mayúscula, una minúscula, un número y un símbolo(+-*/).");
    }
    if (dni && !/^[0-7]\d{7}[A-Z]$/.test(dni)) {
        errores.push("- DNI: ( dígitos (el primero entre 0 y 7) seguidos de una letra mayúscula.");
    }

    if (errores.length > 0) {
        e.preventDefault();
        alert("Corrige los siguientes errores antes de continuar:\n\n" + errores.join("\n"));
        return;
    }

    //configurar metodo y codificacion (dinamicamente) antes del envio
    this.method = document.getElementById('cmethod').value;
    this.enctype = document.getElementById('cencoding').value;
    

    //rellena el campo oculto del navegador
    document.getElementById('cbrowser').value = navigator.userAgent;

});

function tooglePrefs(estado) {
    const checkboxes =  document.querySelectorAll('#prefContainer input[type="checkbox"]');
    checkboxes.forEach(cb => cb.checked = estado);
}
