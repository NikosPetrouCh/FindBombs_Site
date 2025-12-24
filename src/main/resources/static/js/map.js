// MAP

// Initialize the map variable (will be initialized when DOM is ready)
var map = null;

// Updated cities array, now including positions for each photo
var cities = [
    { name: "Thessaloniki", coords: [40.6401, 22.9444], zoom: 12, popup: "Welcome to Thessaloniki!", images: [
        { coords: [40.6401, 22.9444], src: "/images/TImages/Timage1.jpg", alt: "Thessaloniki Image 1" },
        { coords: [40.6450, 22.9400], src: "/images/TImages/Timage2.jpg", alt: "Thessaloniki Image 2" },
        { coords: [40.6385, 22.9500], src: "/images/TImages/Timage3.jpg", alt: "Thessaloniki Image 3" }
    ] },
    { name: "Athens", coords: [37.9838, 23.7275], zoom: 12, popup: "Welcome to Athens!", images: [
        { coords: [37.9838, 23.7275], src: "/images/Aimages/Aimage1.jpg", alt: "Athens Image 1" },
        { coords: [37.9850, 23.7300], src: "/images/Aimages/Aimage2.jpg", alt: "Athens Image 2" },
        { coords: [37.9815, 23.7250], src: "/images/Aimages/Aimage3.jpg", alt: "Athens Image 3" }
    ] },
    { name: "Iraklion", coords: [35.3387, 25.1442], zoom: 12, popup: "Welcome to Iraklion!", images: [
        { coords: [35.3387, 25.1442], src: "/images/IImages/Iimage1.jpg", alt: "Iraklion Image 1" },
        { coords: [35.3400, 25.1450], src: "/images/IImages/Iimage2.jpg", alt: "Iraklion Image 2" },
        { coords: [35.3370, 25.1460], src: "/images/IImages/Iimage3.jpg", alt: "Iraklion Image 3" }
    ] }
];

// Function to update the map with city pins and display the corresponding image when clicked
function updateMapForCity(city) {
    console.log('updateMapForCity called with:', city);
    const mapElement = document.getElementById('map');
    const homepage = document.getElementById('homepage');
    const mapContainer = document.getElementById('map-container');
    
    console.log('Elements found:', { mapElement, homepage, mapContainer });
    
    if (mapElement) {
        mapElement.style.display = 'block';
        mapElement.style.height = '500px';
        mapElement.style.width = '100%';
        console.log('Map element shown');
    }
    if (homepage) homepage.style.display = 'none';
    if (mapContainer) mapContainer.style.display = 'none';

    // Ensure the map is visible and reset size
    setTimeout(() => {
        if (map) map.invalidateSize(); // Recalculate the map size after visibility change
    }, 300);

    // Clear existing markers and circles
    if (map) {
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
            color: '#ADD8E6', // Light blue color for the border
            fillColor: '#87CEEB', // Lighter blue color for the fill (light sky blue)
            fillOpacity: 0.5, // Adjust fill opacity as needed
            radius: 3000 // Radius in meters
        }).addTo(map);
    }
}

// Function to show the selected image in the slider
function showImageInSlider(imageSrc) {
    const sliderContainer = document.querySelector(`#slider-container`);
    if (sliderContainer) {
        sliderContainer.innerHTML = ""; // Clear existing slider images
        
        // Create a new image element for the slider
        const img = document.createElement("img");
        img.src = imageSrc;
        img.alt = "Selected Image";
        img.className = "slider-image"; // Add the correct class for slider images
        sliderContainer.appendChild(img);
    }
}

// Initialize everything when DOM is ready
document.addEventListener('DOMContentLoaded', function() {
    // Initialize the map
    const mapElement = document.getElementById('map');
    if (mapElement) {
        // Initialize the map (default view over Greece)
        map = L.map('map').setView([39.0742, 21.8243], 6);

        // Add the tile layer (OpenStreetMap)
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors',
        }).addTo(map);
    }
    
    // Attach event listeners for areas (cities) - using SVG circles
    const area1 = document.getElementById('area1');
    const area2 = document.getElementById('area2');
    const area3 = document.getElementById('area3');
    
    console.log('Map initialized, looking for circles:', { area1, area2, area3, map });
    
    if (area1) {
        console.log('Adding click listener to area1 (Thessaloniki)');
        area1.addEventListener('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
            console.log('Area1 clicked!', map, cities[0]);
            if (map) {
                updateMapForCity(cities[0]); // Thessaloniki
            } else {
                console.error('Map not initialized!');
            }
        });
    } else {
        console.error('area1 not found!');
    }
    
    if (area2) {
        console.log('Adding click listener to area2 (Athens)');
        area2.addEventListener('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
            console.log('Area2 clicked!', map, cities[1]);
            if (map) {
                updateMapForCity(cities[1]); // Athens
            } else {
                console.error('Map not initialized!');
            }
        });
    } else {
        console.error('area2 not found!');
    }
    
    if (area3) {
        console.log('Adding click listener to area3 (Iraklion)');
        area3.addEventListener('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
            console.log('Area3 clicked!', map, cities[2]);
            if (map) {
                updateMapForCity(cities[2]); // Iraklion
            } else {
                console.error('Map not initialized!');
            }
        });
    } else {
        console.error('area3 not found!');
    }
});

