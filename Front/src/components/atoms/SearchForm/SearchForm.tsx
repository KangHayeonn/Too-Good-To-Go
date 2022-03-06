import React, { useState, useEffect } from 'react';
import axios from "axios";
import { useDispatch } from 'react-redux';
import styled from "@emotion/styled";
import SearchIcon from "@mui/icons-material/Search";
import { getAccessToken } from '../../../helpers/tokenControl';
import { updateMenuItems } from '../../../features/shopFeatures/updateMenuItemsSlice';
import { updateKeywords, updatePageTotalNum, updateKeyword } from '../../../features/shopFeatures/updateKeywordsSlice';

const BASE_URL = "http://54.180.134.20";

const SearchForm: React.FC = () => {
    const [word, setWord] = useState<string>("");
    const [btnOn, setBtnOn] = useState<boolean>(false);
    const dispatch = useDispatch();
    
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const handleOnChange = (e :any) => {
        setWord(e.target.value);
        setBtnOn(false);
    }

    const getProductSearch = (e : React.FormEvent | React.MouseEvent) => {
        e.preventDefault();
        axios.get(
            `${BASE_URL}/api/search`,
            {
                params: {keyword: word},
                headers: { Authorization: `Bearer ${getAccessToken()}`}
            }).then((res)=> {
                dispatch(updateMenuItems(res.data.data.products));
                dispatch(updatePageTotalNum(res.data.data.totalNum));
            }).catch((e) => { console.log("search api 실패 ", e)});
        
        setBtnOn(true);
        dispatch(updateKeywords(true));
        dispatch(updateKeyword(word));
    }
    
    return (
        <SearchBar>
            <form
                onSubmit={(e) => { getProductSearch(e) } }>
                <input
                    className="searchText"
                    type="text"
                    placeholder="검색어를 입력해주세요."
                    name="keyword"
                    onChange={handleOnChange}
                />
                <SearchIcon id="searchIcon" type="button" onClick={(e) => { getProductSearch(e) } } />
            </form>
            {btnOn? <ResultBar><b>{word}</b>(으)로 검색한 결과입니다.</ResultBar>: <></>}
        </SearchBar>
    );
};

const ResultBar = styled.div`
    width: 750px;
    padding-top: 11px;
    padding-bottom: 11px;
    margin-left: 53px;
    background: #F4F0F0;
`

const SearchBar = styled.div`
    position: relative;

    input.searchText {
        outline-style: none;
        border-radius: 27px;
        border: 2px solid #BFBFBF;
        width: 800px;
        height: 40px;
        font-size: 16px;
        font-weight: 500;
        line-height: 16px;
        color: rgb(0, 0, 0);
        text-indent: 15px;
        padding-left: 10px;
        margin-left: 20px;
    }

    #searchIcon {
        color: #000;
        position: absolute;
        right: 15px;
        top: 8px;
        width: 30px;
        height: 30px;
    }
`

export default SearchForm;