/**
 * Java parser for the MRZ records, as specified by the ICAO organization.
 * Copyright (C) 2011 Innovatrics s.r.o.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.innovatrics.mrz.records;

import com.innovatrics.mrz.MrzFinderUtil;
import com.innovatrics.mrz.MrzNotFoundException;
import com.innovatrics.mrz.MrzParseException;
import com.innovatrics.mrz.MrzParser;
import com.innovatrics.mrz.types.MrzDate;
import com.innovatrics.mrz.types.MrzDocumentCode;
import com.innovatrics.mrz.types.MrzSex;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link SlovakId2x34}.
 *
 * @author Martin Vysny
 */
public class SlovakId2x34Test {

	private static final String PARSE = "I<SVKNOVAK<<JAN<<<<<<<<<<<<<<<<<<<\n123456<AA5SVK8110251M1801020749313";
	private static final String TOMRZ = "I<SVKNOVAK<<JAN<<<<<<<<<<<<<<<<<<<\n123456<AA5SVK8110251M1801020749313\n";
	private static final String WRAPPED = "xx\n\nyyy\n" + PARSE + "\nZZZZ";

	@Test
	public void testSlovakId234Parsing() throws MrzParseException {
		final SlovakId2x34 r = (SlovakId2x34) MrzParser.parse(PARSE);
		Assert.assertEquals(MrzDocumentCode.TYPE_I, r.getCode());
		Assert.assertEquals('I', r.getCode1());
		Assert.assertEquals('<', r.getCode2());
		Assert.assertEquals("SVK", r.getIssuingCountry());
		Assert.assertEquals("SVK", r.getNationality());
		Assert.assertEquals("749313", r.getOptional());
		Assert.assertEquals("123456 AA", r.getDocumentNumber());
		Assert.assertEquals(new MrzDate(18, 1, 2), r.getExpirationDate());
		Assert.assertEquals(new MrzDate(81, 10, 25), r.getDateOfBirth());
		Assert.assertEquals(MrzSex.MALE, r.getSex());
		Assert.assertEquals("NOVAK", r.getSurname());
		Assert.assertEquals("JAN", r.getGivenNames());
	}

	@Test
	public void testToMrz() {
		final SlovakId2x34 r = new SlovakId2x34();
		r.setCode1('I');
		r.setCode2('<');
		r.setIssuingCountry("SVK");
		r.setNationality("SVK");
		r.setOptional("749313");
		r.setDocumentNumber("123456 AA");
		r.setExpirationDate(new MrzDate(18, 1, 2));
		r.setDateOfBirth(new MrzDate(81, 10, 25));
		r.setSex(MrzSex.MALE);
		r.setSurname("NOVAK");
		r.setGivenNames("JAN");
		Assert.assertEquals(TOMRZ, r.toMrz());
	}

	@Test
	public void testFindMrz() throws MrzNotFoundException, MrzParseException {
		Assert.assertEquals("Did not find MRZ", PARSE.trim(), MrzFinderUtil.findMrz(PARSE));
	}

	@Test
	public void testFindMrzWrapped() throws MrzNotFoundException, MrzParseException {
		Assert.assertEquals("Did not find wrapped MRZ", PARSE.trim(), MrzFinderUtil.findMrz(WRAPPED));
	}

}
