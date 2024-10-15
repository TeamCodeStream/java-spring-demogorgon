import { useState } from 'react';
import ReactPaginate from 'react-paginate';

export function usePaging<T>({
  itemsPerPage,
  items,
  pageRangeDisplayed = 5,
}: {
  itemsPerPage: number;
  items: T[];
  pageRangeDisplayed?: number;
}) {
  const [itemOffset, setItemOffset] = useState(0);

  const endOffset = itemOffset + itemsPerPage;
  console.log(`Loading items from ${itemOffset} to ${endOffset}`);
  const currentItems = items.slice(itemOffset, endOffset);
  const pageCount = Math.ceil(items.length / itemsPerPage);

  const handlePageClick = (event: { selected: number }) => {
    const newOffset = (event.selected * itemsPerPage) % items.length;
    console.log(`User requested page number ${event.selected}, which is offset ${newOffset}`);
    setItemOffset(newOffset);
  };
  const paginator = (
    <ReactPaginate
      containerClassName={'pagination'}
      pageClassName={'page-item'}
      activeClassName={'active'}
      breakLabel="..."
      nextLabel="next >"
      onPageChange={handlePageClick}
      pageRangeDisplayed={pageRangeDisplayed}
      pageCount={pageCount}
      previousLabel="< previous"
      renderOnZeroPageCount={null}
    />
  );
  return [currentItems, paginator];
}
