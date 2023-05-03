//todo якось треба зробити, щоб вікно вискакувало лише 1 раз
const body = document.querySelector('body');

function checkPageStatus() {

    const regex = /isbn=([0-9]+)/;
    const regex2 = /already=(true+)/;
    const regex3 = /notAvailable=(true+)/;
    const match = window.location.search.match(regex);
    const match2 = window.location.search.match(regex2);
    const match3 = window.location.search.match(regex3);
    if (match2 !== null) {

        const badPopup = document.createElement('div');
        badPopup.innerHTML = `Ви вже замовили цю книжку`;

        badPopup.style.position = 'fixed';
        badPopup.style.bottom = '20px';
        badPopup.style.right = '-350px';
        badPopup.style.padding = '20px';
        badPopup.style.backgroundColor = '#f63434';
        badPopup.style.border = '1px solid black';
        badPopup.style.zIndex = '9999';


        body.appendChild(badPopup);

        setTimeout(function () {
            badPopup.style.transition = "all 1s ease-in-out";
            badPopup.style.right = "70px";
        }, 300);
        setTimeout(function () {
            badPopup.style.transition = "all 1s ease-in-out";
            badPopup.style.right = "-350px";
        }, 3000);
        setTimeout(function () {
            badPopup.remove();
        }, 5000);
    } else if (match3 !== null) {
        const badPopup = document.createElement('div');
        badPopup.innerHTML = `Наразі немає в наявності книги з ISBN `;
        badPopup.style.position = 'fixed';
        badPopup.style.bottom = '20px';
        badPopup.style.right = '-300px';
        badPopup.style.padding = '20px';
        badPopup.style.backgroundColor = '#f63434';
        badPopup.style.border = '1px solid black';
        badPopup.style.zIndex = '9999';


        body.appendChild(badPopup);

        setTimeout(function () {
            badPopup.style.transition = "all 1s ease-in-out";
            badPopup.style.right = "70px";
        }, 300);
        setTimeout(function () {
            badPopup.style.transition = "all 1s ease-in-out";
            badPopup.style.right = "-300px";
        }, 3000);
        setTimeout(function () {
            badPopup.remove();
        }, 5000);
    } else if (match !== null) {

        const isbn = match[1];
        console.log(isbn);
        const successPopup = document.createElement('div');
        successPopup.innerHTML = `Книга з ISBN ${isbn} успішно додана!`;

        successPopup.style.position = 'fixed';
        successPopup.style.bottom = '20px';
        successPopup.style.right = '-300px';
        successPopup.style.padding = '20px';
        successPopup.style.backgroundColor = '#76ad19';
        successPopup.style.border = '1px solid black';
        successPopup.style.zIndex = '9999';


        body.appendChild(successPopup);

        setTimeout(function () {
            successPopup.style.transition = "all 1s ease-in-out";
            successPopup.style.right = "70px";
        }, 300);
        setTimeout(function () {
            successPopup.style.transition = "all 1s ease-in-out";
            successPopup.style.right = "-300px";
        }, 3000);
        setTimeout(function () {
            successPopup.remove();
        }, 5000);
    }
}

document.addEventListener('visibilitychange', checkPageStatus);

checkPageStatus();