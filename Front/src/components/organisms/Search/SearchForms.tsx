import React from 'react';
import axios from 'axios';
import styled from '@emotion/styled';
import { useDispatch } from "react-redux";
import SearchForm from '../../atoms/SearchForm/SearchForm';
import SearchKeywordsTag from '../../atoms/SearchKeywordsTag/SeacrhKeywordsTag';
import { getAccessToken } from '../../../helpers/tokenControl';
import { updateKeywords } from '../../../features/shopFeatures/updateKeywordsSlice';

const BASE_URL = "http://54.180.134.20";

const SearchForms:React.FC = () => {
    const dispatch = useDispatch();
    
    const deleteAllKeywords = () => {
        axios.delete(
            `${BASE_URL}/api/search/keywords/all`,
            {
                headers: { Authorization: `Bearer ${getAccessToken()}`}
            }
        );
        // eslint-disable-next-line no-unneeded-ternary
        dispatch(updateKeywords(true));
    };

    return (
        <Wrapper>
            <SearchForm />
            <KeywordTagsForm>
                <Top>
                    <Title>최근 검색어</Title>
                    <DeleteAllBtn type="button" onClick={() => { deleteAllKeywords() }}>전체 삭제</DeleteAllBtn>
                </Top>
                <SearchKeywordsTag /> 
            </KeywordTagsForm>
        </Wrapper>
    );
};

const Wrapper = styled.div`
    display: block;
`

const Title = styled.div`
    color: #363636;
    font-weight: 700;
    font-size: 20px;
    margin-bottom: 1em;
`

const KeywordTagsForm = styled.div`
    display: flex;
    flex-direction : column;
    align-items: flex-start;
    margin-top : 2em;
    margin-left : 1.7em;
`

const Top = styled.div`
    display: flex;
`

const DeleteAllBtn = styled.button`
    height: 1.6em;
    background: #eee;
    font-size: 15px;
    font-weight: 400;
    padding-left: 11px;
    padding-right: 11px;
    border-radius: 27px;
    margin-right: 1em;
    margin-left: 620px;
    margin-top: 5px;
`

export default SearchForms;