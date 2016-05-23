#!/bin/sh

here="${0%/*}"
cmd='JavaApplicationStub'
ulimit -S -n 1000
exec "$here/$cmd" "$@"
