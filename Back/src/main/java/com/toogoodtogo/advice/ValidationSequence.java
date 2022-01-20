package com.toogoodtogo.advice;

import com.toogoodtogo.advice.ValidationGroups.*;
import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, NotEmptyGroup.class, PatternCheckGroup.class })
public interface ValidationSequence {
}
