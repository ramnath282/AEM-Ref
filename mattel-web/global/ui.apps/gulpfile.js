var gulp = require('gulp');
var browserify = require('gulp-browserify');
var babelify = require('babelify');
var rename = require('gulp-rename');
var uglify = require('gulp-uglify-es').default;

jsBaseFolder = './src/main/content/jcr_root/apps/';

// THIS DEFINES THE GULP TASK
gulp.task('compile-js', function() {  
  return gulp.src([jsBaseFolder + '**/*/babel/*.js'],  {base: './src/main/content/jcr_root/apps/'}) 
    .pipe(browserify({
    transform: [babelify.configure({
        presets: ['env']
      })],
  }))	
  .pipe(rename(function (path) {	
    path.dirname += 'CompiledJs';
  }))
  .pipe(uglify(/* options */))
    .pipe(gulp.dest('./src/main/content/jcr_root/apps/'))
});

gulp.task('default', ['compile-js']); 
