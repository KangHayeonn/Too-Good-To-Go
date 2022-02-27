import React from 'react';
import styled from '@emotion/styled';

const SeacrhKeywordsTag = () => {
    return (
        <Wrapper>
            <Keyword type="button"># bhc</Keyword>
            <Keyword type="button"># 치킨</Keyword>
            <Keyword type="button"># 치</Keyword>
            <Keyword type="button"># 김치찜</Keyword>
            <Keyword type="button"># 피자</Keyword>
        </Wrapper>
    );
};

const Wrapper = styled.div`
    width: 100%;
    height: 3em;
    border-bottom: 3px solid #eee;
    display: flex;
`

const Keyword = styled.button`
    height: 1.6em;
    background: #363636;
    font-size: 16px;
    font-weight: 300;
    padding-left: 1.1em;
    padding-right: 1.2em;
    color: white;
    border-radius: 27px;
    margin-right: 1em;
`

export default SeacrhKeywordsTag;