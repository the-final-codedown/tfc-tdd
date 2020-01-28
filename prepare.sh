#!/bin/bash

PROTO_DEST=src/main/proto/
rm $PROTO_DEST*
cp ../tfc-transfer-validator/src/proto/tfc-transfer-validator.proto $PROTO_DEST
cp ../tfc-cap-updater/src/proto/tfc-cap-updater.proto $PROTO_DEST