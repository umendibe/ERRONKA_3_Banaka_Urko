/* Menu hamburguesa kontrolatzeko JavaScript */
document.addEventListener('DOMContentLoaded', () => {
    const menuToggle = document.getElementById('menu-toggle');
    const menu = document.getElementById('menu');

    if (menuToggle && menu) {
        menuToggle.addEventListener('click', () => {
            menu.classList.toggle('aktibo');
            menuToggle.classList.toggle('aktibo');
        });

        /* Menua itxi esteka bat klik egitean */
        const menuLinks = menu.querySelectorAll('a');
        menuLinks.forEach(link => {
            link.addEventListener('click', () => {
                menu.classList.remove('aktibo');
                menuToggle.classList.remove('aktibo');
            });
        });
    }
});
