$(document).ready(function() {
  barChart();
  lineChart();
  areaChart();
  donutChart();

  $(window).resize(function() {
    window.barChart.redraw();
    window.lineChart.redraw();
    window.areaChart.redraw();
    window.donutChart.redraw();
  });
});

function barChart() {
  window.barChart = Morris.Bar({
    element: 'bar-chart',
    data: [
      { y: '2021', a: 20, BANNATI: 2 },
      { y: '2022', a: 100,  b: 5 },
      { y: '2023', a: 150,  b: 17 },
      { y: '2024', a: 75,  b: 3 },
      { y: '2025', a: 200,  b: 40 },
      { y: '2026', a: 600,  b: 100 },
      { y: '2027', a: 1000, b: 80 }
    ],
    xkey: 'y',
    ykeys: ['a', 'b'],
    labels: ['REGISTRATI ', 'BANNATI'],
    lineColors: ['#1e88e5','#ff3321'],
    lineWidth: '3px',
    resize: true,
    redraw: true
  });
}

function lineChart() {
  window.lineChart = Morris.Line({
    element: 'line-chart',
    data: [
       { y: '2021', a: 10, b: 2 },
      { y: '2022', a: 100,  b: 5 },
      { y: '2023', a: 150,  b: 17 },
      { y: '2024', a: 75,  b: 3 },
      { y: '2025', a: 200,  b: 40 },
      { y: '2026', a: 600,  b: 100 },
      { y: '2027', a: 1000, b: 80 }
    ],
    xkey: 'y',
    ykeys: ['a', 'b'],
    labels: ['REGISTRATI', 'NON REGISTRATI'],
    lineColors: ['#1e88e5','#ff3321'],
    lineWidth: '3px',
    resize: true,
    redraw: true
  });
}

function areaChart() {
  window.areaChart = Morris.Area({
    element: 'area-chart',
    data: [
      { y: '2021', a: 20, b: 10 },
      { y: '2022', a: 300,  b: 80},
      { y: '2023', a: 1500,  b: 1000 },
      { y: '2024', a: 7500,  b: 6000 },
      { y: '2025', a: 2850,  b: 5000},
      { y: '2026', a: 8100,  b: 4000 },
      { y: '2027', a: 5000, b: 2000 }
    ],
    xkey: 'y',
    ykeys: ['a', 'b'],
    labels: ['CARICATO', 'SCARICATO'],
    lineColors: ['#1e88e5','#ff3321'],
    lineWidth: '3px',
    resize: true,
    redraw: true
  });
}

function donutChart() {
  window.donutChart = Morris.Donut({
  element: 'donut-chart',
  data: [
    {label: "Materiale scaricato", value: 50},
    {label: "Materiale caricato", value: 120},
    {label: "Utenti registrati", value: 300},
    {label: "Utenti bannati", value: 20},
   
  ],
  resize: true,
  redraw: true
});
}

function pieChart() {
  var paper = Raphael("pie-chart");
paper.piechart(
  100, // pie center x coordinate
  100, // pie center y coordinate
  90,  // pie radius
   [18.373, 18.686, 2.867, 23.991, 9.592, 0.213], // values
   {
   legend: ["Windows/Windows Live", "Server/Tools", "Online Services", "Business", "Entertainment/Devices", "Unallocated/Other"]
   }
 );
} 