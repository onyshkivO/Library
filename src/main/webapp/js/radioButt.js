// const existRadio = document.getElementById("exist");
// const newRadio = document.getElementById("new");
// const publicationSelect = document.querySelector(".exist");
// const publicationInput = document.querySelector(".new");
// existRadio.addEventListener("change", () => {
//     if (existRadio.checked){
//         publicationSelect.style.display = "block";
//         publicationInput.style.display = "none";
//     } else if (newRadio.checked){
//         console.log(1);
//         publicationSelect.style.display = "none";
//         publicationInput.style.display = "block";
//     }
//
// });

const existRadio = document.getElementById("exist");
const newRadio = document.getElementById("new");
const publicationSelect = document.querySelector(".exist"); // використовуємо querySelector замість getElementsByClassName, щоб отримати перший відповідний елемент
const publicationInput = document.querySelector(".new");

// обробник події change для радіо кнопок
existRadio.addEventListener("change", () => {
    if (existRadio.checked) { // якщо вибрано радіо кнопку "Exist publication"
        publicationSelect.style.display = "block"; // показуємо select
        publicationInput.style.display = "none"; // приховуємо input
    } else { // якщо вибрано радіо кнопку "New publication"
        publicationSelect.style.display = "none"; // приховуємо select
        publicationInput.style.display = "block"; // показуємо input
    }
});

newRadio.addEventListener("change", () => {
    if (newRadio.checked) { // якщо вибрано радіо кнопку "New publication"
        publicationSelect.style.display = "none"; // приховуємо select
        publicationInput.style.display = "block"; // показуємо input
    } else { // якщо вибрано радіо кнопку "Exist publication"
        publicationSelect.style.display = "block"; // показуємо select
        publicationInput.style.display = "none"; // приховуємо input
    }
});