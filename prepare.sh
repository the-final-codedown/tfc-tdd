#!/bin/bash

PROTO_DEST=src/main/proto/
[[ -d $PROTO_DEST ]] && rm -r $PROTO_DEST
mkdir -p $PROTO_DEST

cp ../tfc-transfer-validator/src/proto/tfc-transfer-validator.proto $PROTO_DEST
cp ../tfc-cap-updater/src/proto/tfc-cap-updater.proto $PROTO_DEST