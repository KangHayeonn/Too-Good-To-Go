import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styled from "@emotion/styled";
import { useSelector, useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import { RootState } from "../../../app/store";
import { initialCards } from '../../../features/shopFeatures/updateMenuItemsSlice';
import { updateKeyword } from '../../../features/shopFeatures/updateKeywordsSlice';

type shopsDataType = {
	id: number;
	name: string;
	image: string;
	discountedPrice: number;
	price: number;
	shopId: number;
	shopName: string;
	shopCategory: Array<string>;
};

// 할인율 계산
const calculatedDiscount = (price: number, discountedPrice: number): number => {
	return Math.ceil((1 - discountedPrice / price) * 100);
};

interface menuMatchType {
	// eslint-disable-next-line react/require-default-props
	menuPaginationNumber: number;
}

const SearchCards:React.FC<menuMatchType> = ({ menuPaginationNumber,}) => {
    const [searchItems, setSearchItems] = useState<shopsDataType[]>([]);
	const dispatch = useDispatch();
	/*
	const searchItems = useSelector((state: RootState) => {
        return state.updateMenuItems;
    })
	*/
	const Keyword = useSelector((state: RootState) => {
        return state.updateKeywords;
    })

	const SEARCH_BOARD_API_BASE_URL = `http://54.180.134.20/api/search?keyword=${
		Keyword.keyword}&page=${menuPaginationNumber - 1}`;

	const BoardService = () => {
		return axios.get(SEARCH_BOARD_API_BASE_URL);
	};

	useEffect(() => {
		BoardService().then(
			(res) => {
				setSearchItems(res.data.data.products); // api가 연결 된 경우 -> back에서 데이터 불러옴
			},
			(error) => {
				console.error(error);
			}
		);
	}, [menuPaginationNumber, Keyword]);
  
    return (
        <Wrapper>
            { (searchItems) ? (
                searchItems.map((row) => (
                    <div key={row.shopId}>
						<Link
							to={{
								pathname: `/shop/${row.shopId}`,
							}}>
                        <SearchCard>
                            <div className="card-img-ctn">
                                <img src={row.image} alt="Food" />
                            </div>
                            <div className="cardInfo">
                                <ShopTitle>
                                    <p className="cardInfo-shopName">
                                        {row.shopName}
                                    </p>
                                    <p className="cardInfo-category">
                                        {row.shopCategory.join(", ")}
                                    </p>
                                </ShopTitle>
                                <p className="cardInfo-shopFoodName">
                                    {row.name}
                                </p>
                                <div className="cardInfo-price">
                                    <p>
                                        {calculatedDiscount(
                                            row.price,
                                            row.discountedPrice
                                        )}
                                        %
                                    </p>
                                    <p>{row.discountedPrice}원</p>
                                    <s>({row.price}원)</s>
                                </div>
                            </div>
                        </SearchCard>
						</Link>
                        <Line /> 
                    </div>   
                ))
            ):(<></>)
        }
        </Wrapper>
    );
};

const Wrapper = styled.div`
	width: 820px;
	height: 730px;
	overflow: visible;
	overflow-x: hidden;
	padding-left: 10px;
    padding-right: 10px;
	margin-right: 25px;
	::-webkit-scrollbar {
		width: 10px;
	}
	::-webkit-scrollbar-thumb {
		background-color: #e7e4e4;
		border-radius: 12px;
		background-clip: padding-box;
		border: 2px solid transparent;
	}
	::-webkit-scrollbar-track {
		background-color: white;
		border-radius: 10px;
		box-shadow: inset 0px 0px 5px white;
	}
    margin-left : 11.5em;
    margin-top : 2em;
`;

const SearchCard = styled.div`
    border: 1px solid #eee;
	width: 805px;
	height: 181px;
	padding: 10px 10px 10px 0;
	margin: 0 25px 20px 0;
	display: flex;
	align-items: center;
	border-radius: 8px;

	transition: 0.3s;

	.card-img-ctn {
		width: 134px;
		margin-right: 9px;
		margin-left: 10px;
	}

	img {
		width: 161px;
		height: 149px;
		overflow: hidden;
		margin-left: 14px;
		border-radius: 3px;
	}

	.cardInfo {
		display: flex;
		align-items: flex-start;
		flex-direction: column;
		padding-left: 50px;
		width: 550px;
	}

	.cardInfo > * {
		margin: 2px;
		margin-left: 14px;
	}

	.cardInfo-shopName {
        font-size: 22px;
		font-weight: 700;
        margin-bottom: 13px;
        padding-right: 11px;
        border-right : 3px solid #E2E2E2;
		color: black;
	}

    .cardInfo-category {
        font-size: 22px;
        margin-bottom: 13px;
        margin-left: 11px;
        color: #58C656;
        font-weight: 400;
    }

	.cardInfo-shopFoodName {
		font-size: 18px;
		color: black;
	}

	.cardInfo-price {
		display: flex;
		font-size: 17px;
		font-weight: 500;
		color: black;
	}

	.cardInfo-price > p:first-of-type {
		color: #2f8d09;
		padding-right: 5px;
		font-weight: 700;
	}
`;

const ShopTitle = styled.div`
    display : flex;
`

const Line = styled.div`
    margin-top: 1.5em;
    margin-bottom: 1.5em;
    border-bottom: 3px solid #eee;
`

export default SearchCards;