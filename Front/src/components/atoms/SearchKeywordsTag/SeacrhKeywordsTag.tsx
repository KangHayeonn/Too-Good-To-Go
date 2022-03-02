import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styled from '@emotion/styled';
import { getAccessToken } from '../../../helpers/tokenControl';

const BASE_URL = "http://54.180.134.20";

const SeacrhKeywordsTag: React.FC = () => {
    const [keywords, setKeywords] = useState<Array<string>>([]);

    useEffect(() => {
        axios.get(
            `${BASE_URL}/api/search/keywords`,
            {
                headers: { Authorization: `Bearer ${getAccessToken()}`}
            }).then((res)=> {
                setKeywords(res.data.data);
            }).catch((e) => { console.log("최근 검색어 api 실패 ", e)});
    }, []);

    const handleOnKeyword = ((row : string) => {
        axios.delete(
            `${BASE_URL}/api/search/keywords`,
            {
                params: {keyword: row},
                headers: { Authorization: `Bearer ${getAccessToken()}`}
            }
        );
    })

    return (
        <Wrapper>
            {keywords.map((row) => (
                <Keyword type="button" key={row} onClick={ () => { handleOnKeyword(row) } }># {row}</Keyword>
            ))}
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