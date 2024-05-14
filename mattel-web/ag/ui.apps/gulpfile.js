var gulp = require('gulp');
var browserify = require('gulp-browserify');
var babelify = require('babelify');
var rename = require('gulp-rename');
var uglify = require('gulp-uglify-es').default;
var csso = require('gulp-csso');
var autoprefixer = require('gulp-autoprefixer');

jsBaseFolder = './src/main/content/jcr_root/apps/';

//Set the browser that you want to support
const AUTOPREFIXER_BROWSERS = [
  'ie >= 10',
  'ie_mob >= 10',
  'ff >= 30',
  'chrome >= 34',
  'safari >= 7',
  'opera >= 23',
  'ios >= 7',
  'android >= 4.4',
  'bb >= 10'
]

// THIS DEFINES THE GULP TASK

gulp.task('styles', function () {
    return gulp.src('./target/classes/apps/mattel/ecomm/ag/clientlibs/clientlib-base-ag/css/ag.css')
        // Auto-prefix css styles for cross browser compatibility
        //.pipe(autoprefixer({browsers: AUTOPREFIXER_BROWSERS}))
        // Minify the file
        .pipe(csso())
        .pipe(rename({suffix: '-style.min'}))
        // Output
        .pipe(gulp.dest('./target/classes/apps/mattel/ecomm/ag/clientlibs/clientlib-base-ag/css/'));
});

gulp.task('default', ['styles']);