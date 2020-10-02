const mix = require('laravel-mix');

mix.js('resources/js/app.js', 'resources/static/js')
   .sass('resources/sass/app.scss', 'resources/static/css');