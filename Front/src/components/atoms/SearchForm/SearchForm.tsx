import React from 'react';
import styled from "@emotion/styled";
import SearchIcon from "@mui/icons-material/Search";

const SearchForm = () => {
    return (
        <SearchBar>
            <form>
                <input
                    className="searchText"
                    type="text"
                    placeholder="검색어를 입력해주세요."
                />
                <SearchIcon id="searchIcon" type="button" onClick={() => { console.log("click") } } />
            </form>
            <ResultBar><b>bhc</b>(으)로 검색한 결과입니다.</ResultBar>
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