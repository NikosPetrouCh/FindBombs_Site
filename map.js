// MAP

// Initialize the map (default view over Greece)
var map = L.map('map').setView([39.0742, 21.8243], 6);

// Add the tile layer (OpenStreetMap)
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors',
}).addTo(map);

// Updated cities array, now including positions for each photo
var cities = [
    { name: "Thessaloniki", coords: [40.6401, 22.9444], zoom: 12, popup: "Welcome to Thessaloniki!", images: [
        { coords: [40.6401, 22.9444], src: "assets/Timages/Timage1.jpg", alt: "Thessaloniki Image 1" },
        { coords: [40.6450, 22.9400], src: "assets/Timages/Timage2.jpg", alt: "Thessaloniki Image 2" },
        { coords: [40.6385, 22.9500], src: "assets/Timages/Timage3.jpg", alt: "Thessaloniki Image 3" }
    ] },
    { name: "Athens", coords: [37.9838, 23.7275], zoom: 12, popup: "Welcome to Athens!", images: [
        { coords: [37.9838, 23.7275], src: "assets/Aimages/Aimage1.jpg", alt: "Athens Image 1" },
        { coords: [37.9850, 23.7300], src: "assets/Aimages/Aimage2.jpg", alt: "Athens Image 2" },
        { coords: [37.9815, 23.7250], src: "assets/Aimages/Aimage3.jpg", alt: "Athens Image 3" }
    ] },
    { name: "Iraklion", coords: [35.3387, 25.1442], zoom: 12, popup: "Welcome to Iraklion!", images: [
        { coords: [35.3387, 25.1442], src: "assets/Iimages/Iimage1.jpg", alt: "Iraklion Image 1" },
        { coords: [35.3400, 25.1450], src: "assets/Iimages/Iimage2.jpg", alt: "Iraklion Image 2" },
        { coords: [35.3370, 25.1460], src: "assets/Iimages/Iimage3.jpg", alt: "Iraklion Image 3" }
    ] }
];

// Function to update the map with city pins and display the corresponding image when clicked
function updateMapForCity(city) {
    document.getElementById('map').style.display = 'block';
    document.getElementById('homepage').style.display = 'none';
    document.getElementById('map-container').style.display = 'none';

    // Ensure the map is visible and reset size
    setTimeout(() => {
        map.invalidateSize(); // Recalculate the map size after visibility change
    }, 300);

    // Clear existing markers and circles
    map.eachLayer(function (layer) {
        if (layer instanceof L.Marker || layer instanceof L.Circle) {
            map.removeLayer(layer);
        }
    });

    // Set the map view to the city's coordinates and zoom level
    map.setView(city.coords, city.zoom);

    // Add the main city marker
    L.marker(city.coords).addTo(map)
        .bindPopup(city.popup)
        .openPopup();

    // Add markers for each image location for the selected city
    city.images.forEach(function (imageData) {
        var marker = L.marker(imageData.coords).addTo(map)
            .bindPopup(`<img src="${imageData.src}" alt="${imageData.alt}" style="width: 100px; height: 100px;" />`)
            .on('click', function() {
                showImageInSlider(imageData.src);
            });
    });

    // Add circle for the city with a lighter blue color
    L.circle(city.coords, {
        color: '#ADD8E6', // Light blue color for the border (or use RGB: 'rgb(173, 216, 230)')
        fillColor: '#87CEEB', // Lighter blue color for the fill (light sky blue)
        fillOpacity: 0.5, // Adjust fill opacity as needed
        radius: 3000 // Radius in meters
    }).addTo(map);

}

// Function to show the selected image in the slider
function showImageInSlider(imageSrc) {
    const sliderContainer = document.querySelector(`#slider-container`);
    sliderContainer.innerHTML = ""; // Clear existing slider images
    
    // Create a new image element for the slider
    const img = document.createElement("img");
    img.src = imageSrc;
    img.alt = "Selected Image";
    img.className = "slider-image"; // Add the correct class for slider images
    sliderContainer.appendChild(img);
}

// Attach event listeners for areas (cities)
document.getElementById('area1').addEventListener('click', function () {
    updateMapForCity(cities[0]); // Thessaloniki
    // Ensure the container becomes a flexbox
    document.getElementById('map-slideshow-container').style.display = 'flex';
    document.getElementById('wallsThessaloniki').style.display = 'block';
});

document.getElementById('area2').addEventListener('click', function () {
    updateMapForCity(cities[1]); // Athens
    // Ensure the container becomes a flexbox
    document.getElementById('map-slideshow-container').style.display = 'flex';
    document.getElementById('wallsAthens').style.display = 'block';
});

document.getElementById('area3').addEventListener('click', function () {
    updateMapForCity(cities[2]); // Iraklion
    // Ensure the container becomes a flexbox
    document.getElementById('map-slideshow-container').style.display = 'flex';
    document.getElementById('wallsIraklion').style.display = 'block';
});






