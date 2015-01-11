package com.dron.sender.pattern.services.transformers;

import com.dron.sender.controllers.root.models.UISequence;
import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.sequence.models.Sequence;

public class SequenceTransformer implements
		IBaseTransformer<Sequence, UISequence> {

	@Override
	public UISequence transform(final Sequence sequence,
			final UISequence uiSequence) {

		// Fill Params for sequence
		TransformerFactory.transformEntity(sequence.getParams(),
				uiSequence.getUIParams(), TransformKey.PARAMS);

		// Fill FutureParams for each plugin
		TransformerFactory.transformEntity(sequence.getPlugins(),
				uiSequence.getMapFutureParams(),
				TransformKey.FILL_PLUGIN_FUTURE_PARAMS);

		return uiSequence;
	}

	@Override
	public Sequence reverseTransform(final Sequence sequence,
			final UISequence uiSequence) {
		// Fill Params for sequence
		TransformerFactory.reverseTransformEntity(sequence.getParams(),
				uiSequence.getUIParams(), TransformKey.PARAMS);

		// Fill FutureParams for each plugin
		TransformerFactory.reverseTransformEntity(sequence.getPlugins(),
				uiSequence.getMapFutureParams(),
				TransformKey.FILL_PLUGIN_FUTURE_PARAMS);

		return sequence;
	}

}
