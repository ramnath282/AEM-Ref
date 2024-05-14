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
gulp.task('compile-js', function() {  
  return gulp.src([jsBaseFolder + '**/*/babel/*.js'],  {base: './src/main/content/jcr_root/apps/'}) 
    .pipe(browserify({
    transform: [babelify.configure({
        presets: ['es2015']
      })],
  }))	
  .pipe(rename(function (path) {	
    path.dirname += 'CompiledJs';
  }))
  .pipe(uglify(/* options */))
    .pipe(gulp.dest('./src/main/content/jcr_root/apps/'))
});

gulp.task('styles', function () {
    return gulp.src(['./target/classes/apps/mattel/ecomm/shared/clientlibs/clientlib-base/css/ecomm.css',
        './target/classes/apps/mattel/ecomm/shared/components/content/mixedMediaViewer/mixedMediaViewer/css/mix.css'],  {base: './target/classes/apps/mattel/ecomm/shared/'})
        // Auto-prefix css styles for cross browser compatibility
        //.pipe(autoprefixer({browsers: AUTOPREFIXER_BROWSERS}))
        // Minify the file
        .pipe(csso())
        // Output
        .pipe(gulp.dest('./target/classes/apps/mattel/ecomm/shared/'));
});

gulp.task('default', ['compile-js']);