package kr.or.connect.reservation.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public abstract class OneToManyResultSetExtractor<R, C, K> implements ResultSetExtractor<List<R>> {

    public enum  ExpectedResults {
        ANY,
        ONE_AND_ONELY_ONE,
        ONE_OR_NONE,
        AT_LEAST_ONE
    }

    protected final ExpectedResults expectedResults;
    protected final RowMapper<R> rootMapper;
    protected final RowMapper<C> childMapper;

    public OneToManyResultSetExtractor(RowMapper<R> rootMapper, RowMapper<C> childMapper) {
        this(rootMapper, childMapper, null);
    }

    public OneToManyResultSetExtractor(RowMapper<R> rootMapper, RowMapper<C> childMapper, ExpectedResults expectedResults) {
        this.rootMapper = rootMapper;
        this.childMapper = childMapper;

        Assert.notNull(rootMapper, "Root RowMapper must be not null!");
        Assert.notNull(childMapper, "Child RowMapper must be not null!");

        this.expectedResults = expectedResults == null ? ExpectedResults.ANY : expectedResults;
    }
    
    @Override
    public List<R> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<R> results = new ArrayList<>();
        int row = 0;
        boolean more = rs.next();
        if (more) {
            row++;
        }
        while (more) {
            R root = rootMapper.mapRow(rs, row);
            K primaryKey = mapPrimaryKey(rs);
            if (mapForeignKey(rs) != null) {
                while (more && primaryKey.equals(mapForeignKey(rs))) {
                    addChild(root, childMapper.mapRow(rs, row));
                    more = rs.next();
                    if (more) {
                        row++;
                    }
                }
            } else {
                more = rs.next();
                if (more) {
                    row++;
                }
            }
            results.add(root);
        }
        if ((expectedResults == ExpectedResults.ONE_AND_ONELY_ONE || expectedResults == ExpectedResults.ONE_OR_NONE) &&
                results.size() > 1) {
                throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
        if ((expectedResults == ExpectedResults.ONE_AND_ONELY_ONE || expectedResults == ExpectedResults.AT_LEAST_ONE) &&
                results.size() < 1) {
                throw new IncorrectResultSizeDataAccessException(1, 0);
        }
        return results;
    }

    protected abstract K mapPrimaryKey(ResultSet rs) throws SQLException;

    protected abstract K mapForeignKey(ResultSet rs) throws SQLException;
   
    protected abstract void addChild(R root, C child);
}
